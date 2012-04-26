function [x,fval,it] = ssn(f,gradf,hessf,a,b,x0,itmax,tol)
	x = x0;
	n = length(x);
	it = 0;
	stop = false;
	y = zeros(n,1);
	zero = zeros(n,1);
	c = 0.5;
	itmax = 5;
	
	while( ~stop )
		it = it + 1;
		y1 = y+c*(x-b);
		y2 = y+c*(x-a);
		h = [ feval(gradf,x)-y; y-max(zero,y1)-min(zero,y2) ]
		H = [ feval(hessf,x) -eye(n,n);
				zeros(n,n)    eye(n,n)];
		for k=1:n
			if (y1(k)>0)
				H(n+k,k) = -c;
		    else
				H(n+k,n+k) = H(n+k,n+k) - 1;
		    end
			if (y2(k)<0)
				H(n+k,k) = H(n+k,k) - c;
		    else
				H(n+k,n+k) = H(n+k,n+k) - 1;
		    end
	    end
		H
		q = H\h
		d = q(1:n)
		y = q(n+1:2*n)
		x = x+d
		% Check the stop criteria
		if (norm(feval(gradf,x)) < tol)
			stop = true;
		end
		% If there are too many iterations
		if (it >= itmax)
			stop = true;
        end
    end
    fval = feval(f,x);
end