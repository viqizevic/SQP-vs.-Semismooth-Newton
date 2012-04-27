function [x,fval,it] = ssn(f,gradf,hessf,lambda,a,b,x0,itmax,tol)
	x = x0;
	n = length(x);
	it = 0;
	stop = false;
	y = zeros(n,1);
	zero = zeros(n,1);
	c = lambda
	
	while( ~stop )
		it = it + 1;
		y1 = y+c*(x-b)
		y2 = y+c*(x-a)
		h = [ feval(gradf,x)+y; y-max(zero,y1)-min(zero,y2) ]
		H = [ feval(hessf,x)+lambda*eye(n,n) eye(n,n);
				zeros(n,n)    eye(n,n)];
		for k=1:n
			if (x(k)>b(k))
				H(n+k,k) = c;
		  else
				H(n+k,n+k) = H(n+k,n+k) - 1;
		  end
			if (x(k)<a(k))
				H(n+k,k) = H(n+k,k) + c;
		  else
				H(n+k,n+k) = H(n+k,n+k) - 1;
		  end
	  end
		H
		q = H\h
		d = q(1:n);
		y = q(n+1:2*n);
		x = x+d
		y = min(zero,y+(x-b))+min(zero,y+(x-a))
		% Check the stop criteria
		if (norm(feval(gradf,x)) < tol)
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
