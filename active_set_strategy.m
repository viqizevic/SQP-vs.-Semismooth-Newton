% Function: [x,fval,it] = active_set_strategy(f,gradf,hessf,lamda,a,b,x0,itmax,tol)
%
%  Active Set Strategy solves the problem
%        min ( f(x) + (lambda/2)*|x|^2 )
%         x
%        s.t. a <= x <= b
%
%  Let f : R^n -> R
%  lambda a real number
%  a and b in R^n
function [x,fval,it] = active_set_strategy(f,gradf,hessf,lambda,G,r,x0,itmax,tol)
	x = x0;
	%n = length(x);
	[p,n] = size(G);
	mu = zeros(p,1);
	it = 0;
	stop = false;
	
	while( ~stop )
		it = it + 1;
		
		H = feval(hessf,x);
		A = [H+lambda*eye(n) G';
						zeros(p,n+p) ];
		y = [H*x-feval(gradf,x);
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
		d = feval(gradf,x)+lambda*x+G'*mu;
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
	fval = feval(f,x) + (lambda/2)*norm(x)^2;
end