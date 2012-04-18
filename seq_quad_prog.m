%SQP Sequential Quadratic Programming
%   [x,fval,it] = seq_quad_prog(f,gradf,hessf,A,b,x0,itmax,tol) attempts to solve the problem:
%
%   min f(x) subject to: A*x <= b
%    x
function [x,fval,it] = seq_quad_prog(f,gradf,hessf,A,b,x0,itmax,tol)
	it = 0;
	x = x0;
	stop = false;
	while( ~stop )
		% solve the problem:
		% min 0.5*d'*hessf(x)*d + gradf(x)'*d subject to: A*x + A*d <= b
		%  d
		Q = feval(hessf,x);
		q = feval(gradf,x);
		z = zeros(length(x),1);
		[d, it2] = aktive_mengen_methode(Q,q,[],[],A,b-A*x,z,tol,itmax);
		it = it + it2;
		if( norm(d) < tol )
			stop = true; % => x is the solution
		else
			x = x + d;
		end
        % If there are too many iterations
		if (it >= itmax)
			stop = true;
        end
    end
    fval = feval(f,x);
end

function [x, it] = aktive_mengen_methode(Q,q,A,b,G,r,x0,tol,itmax)
	% Funktion: [x, it] = aktive_mengen_methode(Q,q,A,b,G,r,x0,tol,itmax)
	%
	%   Aktive-Mengen-Methode fuer
	%   (QLU) min f(x) := 0.5*x'*Q*x + q'*x
	%          x
	%         mit Nebenbedingung A*x = b und G*x <= r
	%   (Quadratisches Problem mit linearen Ungleichungsrestriktionen)
	%
	%   Q sei eine symmetriche nxn-Matrix, q aus R^n.
	%   A sei eine mxn-Matrix, m <= n, b aus R^m.
	%   G sei eine pxn-Matrix, r aus R^p.
	%   x0 sei der Startpunkt, wobei x0 die Nebenbedingungen erfuellt.
	%   tol sei die Toleranz fuer das Abbruchskriterium.
	%   itmax sei die maximale Anzahl von Iterationen.
	k = 0;
	x_k = x0;
	m = length(b);
	p = length(r);
	while true
		% Bilde die Matrix G_k := [g_j'], wobei j aus J_k.
		% J_k := { 1<=j<=p | (g_j)'*x_k = r_j }
		% die Indexmenge der in x_k aktiven Ungleichungsrestriktionen.
		%
		l = 1;
		G_k = [];
		for j=1:p
			if ( G(j,:)*x_k == r(j) )
				G_k(l,:) = G(j,:);
				l = l+1;
			end
		end
		B_k = [A; G_k];
		[m_k,n_k] = size(B_k);
		z = zeros(m_k,1);
		% TODO: Was ist wenn m_k > n_k ?
		% Loese das Problem (QLG)_k
		% min 0.5*d'*Q*d + (Q*x_k+q)'*d
		%  x
		% mit Nebenbedingung B_k = 0
		%
		[d_k,v_k] = nullraum_verfahren(Q,(Q*x_k+q),B_k,z);
		if ~isempty(v_k)
			lambda_k = v_k(1:m,1);
			miu_k = v_k(m+1:m_k);
		else
			miu_k = [];
		end
		if norm(d_k) < tol
			% Falls d_k = 0 und miu_k >= 0 : STOP
			%
			miu_nicht_negative = true;
			p_k = length(miu_k);
			for j=1:p_k
				if miu_k(j) < 0
					miu_nicht_negative = false;
				end
			end
			if miu_nicht_negative
				break;
			end
			% Falls d_k = 0 und es existiert j, so dass miu_k(j) < 0
			% Dann Inaktivierungsschritt.
			%
			% Bestimme j_min, so dass  miu_k(j_min) das Minimum in miu ist.
			j_min = 1;
			min_elem = miu_k(1);
			for j=2:p_k
				if miu_k(j) < min_elem
					j_min = j;
					min_elem = miu_k(j);
				end
			end
			% Streiche in G_k die Zeile mit dem Index j_min
			if j_min == p_k
				G_k = [];
			else
				H_k = [];
				H_k(1:j_min-1,:) = G_k(1:j_min-1,:);
				H_k(j_min:p_k-1,:) = G_k(j_min+1:p_k,:);
				G_k = H_k;
			end
			% Loese wieder das Problem (QLG)_k
			B_k = [A; G_k];
			[m_k,n_k] = size(B_k);
			z = zeros(m_k,1);
			% min 0.5*d'*Q*d + (Q*x_k+q)'*d
			%  x
			% mit Nebenbedingung B_k = 0
			[d_k,v_k] = nullraum_verfahren(Q,(Q*x_k+q),B_k,z);
			% Nun ist d_k ungleich 0
		end
		% Berechne Scrittweite sigma_k.
		%
		% Bestimme I_k := { j=1,...,p | (g_j)'*d_k > 0 }
		l = 1;
		I_k = [];
		for j=1:p
			if ( G(j,:)*d_k > 0 )
				I_k = [I_k; j];
				l = l+1;
			end
		end
		% tao_k := min { (r_j - (g_j)'*x_k) / ((g_j)'*d_k) | j aus I_k}
		% falls I_k nicht leer, sonst +infty
		% sigma_k := min { 1, tao_k }
		if length(I_k) == 0
			sigma_k = 1;
		else
			l = length(I_k);
			j = I_k(1);
			tao_k = (r(j) - G(j,:)*x_k) / (G(j,:)*d_k);
			for t=2:l
				j = I_k(t);
				if tao_k > (r(j) - G(j,:)*x_k) / (G(j,:)*d_k)
					tao_k = (r(j) - G(j,:)*x_k) / (G(j,:)*d_k);
				end
			end
			sigma_k = min(1,tao_k);
		end
		% Setze x_{k+1} := x_k + sigma_k*d_k und k = k+1.
		%
		x_k = x_k + sigma_k*d_k;
		if sigma_k == 0
			break;
		end
		k = k+1;
		if k >= itmax
			break;
		end
	end
	x = x_k;
	it = k;
end


% Funktion: [x, lambda] = nullraum_verfahren(Q,q,A,b)
%
%   Nullraum-Verfahren fuer
%   (QLG) min f(x) := 0.5*x'*Q*x + q'*x
%          x
%         mit Nebenbedingung A*x = b
%   (Quadratisches Problem mit linearen Gleichungsrestriktionen)
%
%   Dabei sei Q eine symmetriche nxn-Matrix, q aus R^n,
%   A eine mxn-Matrix, m <= n, b aus R^m.
%   A habe vollen Rang.
%   Und es gilt: d'*Q*d >= alpha*||d||^2 fuer alle d aus kern(A)
%   Dann hat das Problem (QLG) eine eindeutig bestimmte Loesung.
%   (Siehe Satz 5.4.13, Walter Alt: Nichtlineare Optimierung, Vieweg, 2002)
%
function [x,lambda] = nullraum_verfahren(Q,q,A,b)
	if ( isempty(A) || isempty(b) )
		x = -Q\q;
		lambda = [];
		return
	end
	[m,n] = size(A);
	% Berechne die QR-Zerlegung von A'
	%
	[P, S] = qr(A'); % Die Funktion qr gibt P und S zurueck, wobei A' = P*S
	H = P';          % Diese H erfuellt H*A' = S
	R = S(1:m,1:m);  % Diese R erfuellt H*A = [R; 0]
	% Berechne h := -H*q und [h1; h2] := h, wobei h1 aus R^m
	%
	h = -H*q;
	h1 = h(1:m,1);
	h2 = h(m+1:n,1);
	% Berechne B := H*Q*H' und
	% [            ]
	% [  B11  B12  ]
	% [            ] := B, wobei B11 eine mxm-Matrix
	% [  B21  B22  ]
	% [            ]
	%
	B = H * Q * H';
	B11 = B(1:m,1:m);
	B12 = B(1:m,m+1:n);
	B21 = B(m+1:n,1:m);
	B22 = B(m+1:n,m+1:n);
	% Berechne x_y Loesung von R'*x_y = b
	% und x_z Loesung von B22*x_z = h2 - B21*x_y
	%
	x_y = R'\b;
	if ~isempty(B22)
		x_z = B22\(h2-B21*x_y);
	else
		x_z = [];
	end
	% Berechne die Loesung x := H'*[x_y; x_z]
	% und lambda Loesung von R*lambda = h1 - B11*x_y - B12*x_z
	%
	x = H'*[x_y; x_z];
	if ~isempty(x_z)
		lambda = R\(h1-B11*x_y-B12*x_z);
	else
		lambda = R\(h1-B11*x_y);
	end
end