% Function: [x,fval,it] = active_set_strategy (f, gradf, hessf,
%                                    A, b, G, r, x0, itmax, tol)
%
%  Attempt to solve the problem
%
%        min f(x)
%         x
%
%  subject to
%
%        A*x  = b
%        G*x <= r
%
%  using the active set strategy.
%
%  f     : The name of the objective function f : R^n -> R.
%  gradf : The name of the function that returns the gradient of f.
%  hessf : The name of the function that returns the hessian matrix of f.
%  A     : A matrix with dimension m x n for the linear equality contraints.
%  b     : A vector with m elements for the linear equality contraints.
%  G     : A matrix with dimension p x n for the linear inequality constraints.
%  r     : A vector with p elements for the linear inequality constraints.
%  x0    : The start point for the algorithm.
%  itmax : The maximal number of iterations allowed.
%  tol   : The bound needed for the stop criteria.
%
function [x,fval,it] = active_set_strategy(f,gradf,hessf,A,b,G,r,x0,itmax,tol)
	it = 0;
	[m,n] = size(A);
	[p,n] = size(G);
	n = length(x0);
	x = x0;
	lambda = zeros(m,1);
	mu = zeros(p,1);
	
	stop = false;
	while( ~stop )
		it = it + 1;
		F = feval(hessf,x);
		H2 = [];
		if (m ~= 0)
			H2 = [A zeros(m,m+p)];
		end
		H = [ F  A'  G';
		      H2;
			zeros(p,n+m+p) ];
		y = [ F*x-feval(gradf,x);
		        b;
						zeros(p,1)];
		for k=1:p
			gk = G(k,1:n);
			if (r(k)-gk*x <= mu(k))
				H(n+m+k,1:n) = gk;
				y(n+m+k) = r(k);
			else
				H(n+m+k,n+m+k) = 1;
			end
		end
		w = H\y;
		x = w(1:n);
		lambda = w(n+1:n+m);
		mu = w(n+m+1:n+m+p);
		
		% Check the stop criteria
		d = feval(gradf,x);
		if (m ~= 0)
			d = d + A'*lambda;
		end
		if (p ~= 0)
			d = d + G'*mu;
		end
		if (norm(d) < tol)
			stop = true;
			if (m ~= 0)
				if (norm(A*x-b) > tol)
					stop = false;
				end
			end
			if (p ~= 0)
				w = min(mu,r-G*x);
				if (norm(w) > tol)
					stop = false;
				end
			end
		end
		% If there are too many iterations
		if (it >= itmax)
			stop = true;
		end
	end
	fval = feval(f,x);
end