function [x,fval,it] = semismooth_newton(f,gradf,hessf,G,r,x0,itmax,tol)
	it = 0;
	[p,n] = size(G);
	x = x0;
	mu = zeros(p,1);
	zero = zeros(n,1);
	
	stop = false;
	while( ~stop )
		it = it + 1;
		h = -[ feval(gradf,x)+G'*mu;
						min(mu,r-G*x) ];
		H = [ feval(hessf,x)  G';
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
	fval = feval(f,x);
end
