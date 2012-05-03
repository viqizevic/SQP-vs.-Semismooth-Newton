% Function: [x,fval,it] = active_set_strategy(f,gradf,hessf,G,r,x0,itmax,tol)
%
%  Active Set Strategy solves the problem
%        min f(x)
%         x
%        s.t. G*x <= r
%
%  Let f be the name of a scalar function f : R^n -> R
%  gradf the name of the function that returns the gradient of f
%  hessf the name of the function that returns the hessian matrix of f
%  G a matrix with dimension p x n
%  r a vector with p elements
%
function [x,fval,it] = active_set_strategy(f,gradf,hessf,G,r,x0,itmax,tol)
	it = 0;
	[p,n] = size(G);
	x = x0;
	mu = zeros(p,1);
	
	stop = false;
	while( ~stop )
		it = it + 1;
		H = feval(hessf,x);
		A = [ H   G';
			zeros(p,n+p) ];
		y = [ H*x-feval(gradf,x);
						zeros(p,1)];
		for k=1:p
			gk = G(k,1:n);
			if (r(k)-gk*x < mu(k))
				A(n+k,1:n) = gk;
				y(n+k) = r(k);
			else
				A(n+k,n+k) = 1;
			end
		end
		w = A\y;
		x = w(1:n);
		mu = w(n+1:n+p);
		
		% Check the stop criteria
		d = feval(gradf,x)+G'*mu;
		if (norm(d) < tol)
			w = min(mu,r-G*x);
			if (norm(w) < tol)
				stop = true;
			end
		end
		% If there are too many iterations
		if (it >= itmax)
			stop = true;
		end
	end
	fval = feval(f,x);
end