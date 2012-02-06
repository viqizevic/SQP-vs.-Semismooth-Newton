%SQP Sequential Quadratic Programming
%   [x, it] = sqp(f,gradf,hessf,A,b,x0,tol) attempts to solve the problem:
%
%   min f(x) subject to: A*x <= b
%    x
function [x, it] = sqp(f,gradf,hessf,A,b,x0,tol,itmax)
	it = 0;
	x = x0;
	stop = false;
	while( ~stop )
			it = it + 1;
			% solve the problem:
			% min 0.5*d'*hessf(x)*d + gradf(x)'*d subject to: A*x + A*d <= b
			%  d
			Q = feval(hessf,x);
			q = feval(gradf,x);
			z = zeros(length(x),1);
			d = aktive_mengen_methode(Q,q,[],[],A,b-A*x,z,tol,itmax)
			if( norm(d) < tol )
				stop = true; % => x is the solution
			else
				x = x + d;
			endif
	endwhile
endfunction

% Funktion: [x, it] = qpnl(Q,q,A,b,G,r,x0,tol)
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
%   tol sei die Toleranz für das Abbruchskriterium.
function [x, it] = aktive_mengen_methode(Q,q,A,b,G,r,x0,tol)
	k = 0;
	x_k = x0;
	m = length(b);
	p = length(r);
	stop = false;
	while ~stop
		% Bilde die Matrix G_k := [g_j'], wobei j aus J_k
		% Dabei sei J_k := { 1<=j<=p | (g_j)'*x_k = r_j }
		% die Indexmenge der in x_k aktiven Ungleichungsrestriktionen.
		%
		l = 1;
		for j=1:p
			if ( G(j,:)*x_k == r(j) )
				G_k(l,:) = G(j,:);
				l = l+1;
			endif
		endfor
		B_k = [A; G_k];
		[m_k,n_k] = size(B_k);
		z = zeros(m_k,1);
		% TODO: Was ist wenn m_k > n_k ?
		% Loese das Problem
		% min 0.5*d'*Q*d + (Q*x_k+q)'*d
		%  x
		% mit Nebenbedingung B_k = 0
		%
		[d_k,v_k] = nullraum_verfahren(Q,(Q*x_k+q),B_k,z);
		lambda_k = v_k(1:m,1);
		miu_k = v_k(m+1:m_k);
		if norm(d_k) < tol
			% Falls d_k = 0 und miu_k >= 0 : STOP
			%
			miu_nicht_negative = true;
			p_k = length(miu_k);
			for j=1:p_k
				if miu_k(j) < 0
					miu_nicht_negative = false;
				endif
			endfor
			if miu_nicht_negative
				x = x_k;
				it = k;
				return
			endif
			% Falls d_k = 0 und es existiert j, so dass miu_k(j) < 0
		endif
	stop = true;
	endwhile
endfunction

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
function [x,lambda] = nullraum_verfahren(Q,q,A,b)
	[m,n] = size(A);
	if ( A == [] || b == [] )
		x = -Q\q;
		lambda = [];
		return
	endif
	% Berechne die QR-Zerlegung von A'
	%
	[P, S] = qr(A'); % Die Funktion qr gibt P und S zurück, wobei A' = P*S
	H = P';          % Diese H erfüllt H*A' = S
	R = S(1:m,1:m);  % Diese R erfüllt H*A = [R; 0]
	% Berechne h := -H*q und [h1; h2] := h, wobei h1 aus R^m
	%
	h = -H*q;
	h1 = h(1:m,1);
	h2 = h(m+1:n,1);
	% Berechne B := H*Q*H' und
	% [          ]
	% [ B11  B12 ]
	% [          ] := B, wobei B11 eine mxm-Matrix
	% [ B21  B22 ]
	% [          ]
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
	if B22 ~= []
		x_z = B22\(h2-B21*x_y);
	else
		x_z = [];
	endif
	% Berechne die Loesung x := H'*[x_y; x_z]
	% und lambda Loesung von R*lambda = h1 - B11*x_y - B12*x_z
	%
	x = H'*[x_y; x_z];
	if x_z ~= []
		lambda = R\(h1-B11*x_y-B12*x_z);
	else
		lambda = R\(h1-B11*x_y);
	endif
endfunction