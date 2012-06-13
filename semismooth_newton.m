%  Function: [x,fval,it,X] = semismooth_newton (f, gradf, hessf,
%                                      A, b, G, r, x0, itmax, tol)
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
%  using the semismooth Newton method.
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
function [x,fval,it,X] = semismooth_newton(f,gradf,hessf,A,b,G,r,x0,itmax,tol)
	it = 0;
	[m,n] = size(A);
	[p,n] = size(G);
	n = length(x0);
	x = x0;
	X(1,:) = x';
	lambda = zeros(m,1);
	mu = zeros(p,1);
	
	stop = false;
	while( ~stop )
		h1 = feval(gradf,x);
		h2 = [];
		h3 = [];
		H2 = [];
		if (m ~= 0)
			h1 = h1 + A'*lambda;
			h2 = A*x - b;
			H2 = [A zeros(m,m+p)];
		end
		if (p ~= 0)
			h1 = h1 + G'*mu;
			h3 = min(mu,r-G*x);
		end
		h = -[ h1;
					 h2;
					 h3 ];
		H = [ feval(hessf,x)  A' G';
						H2;
						zeros(p,n+m+p) ];
		for k=1:p
			gk = G(k,1:n);
			if (r(k)-gk*x < mu(k))
				H(n+m+k,1:n) = -gk;
		  else
				H(n+m+k,n+m+k) = 1;
		  end
	  end
		d = H\h;
		dx = d(1:n);
		dlambda = d(n+1:n+m);
		dmu = d(n+m+1:n+m+p);
		x = x + dx;
		it = it + 1;
		X(it+1,:) = x';
		lambda = lambda + dlambda;
		mu = mu + dmu;
		% Check the stop criteria
		if (norm(d) < tol)
			stop = true;
		end
		% If there are too many iterations
		if (it >= itmax)
			stop = true;
		end
	end
	fval = feval(f,x);
end
