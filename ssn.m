function [x,fval,it] = ssn(f,gradf,hessf,lambda,G,r,x0,itmax,tol)
	x = x0;
	%n = length(x);
	[p,n] = size(G);
	it = 0;
	stop = false;
	mu = zeros(p,1);
	zero = zeros(n,1);
	c = lambda;
	
	while( ~stop )
		it = it + 1;
		h = -[ feval(gradf,x)+lambda*x+G'*mu;
						min(mu,r-G*x) ];
		H = [ feval(hessf,x)+lambda*eye(n,n)  G';
						zeros(p,n+p) ];
		for k=1:p
			gk = G(k,1:n);
			if (r(k)-gk*x < mu(k))
				H(n+k,1:n) = -gk;
		  else
				H(n+k,n+k) = 1;
		  end
	  end
		q = H\h;
		d = q(1:n);
		mu = q(n+1:n+p);
		x = x+d;
		% Check the stop criteria
		if (norm(d) < tol)
			stop = true;
		end
		% If there are too many iterations
		if (it >= itmax)
			stop = true;
		end
	end
	fval = complete_f(f,lambda,x);
end

function y = complete_f(f,lambda,x)
	y = feval(f,x) + (lambda/2)*norm(x)^2;
end
