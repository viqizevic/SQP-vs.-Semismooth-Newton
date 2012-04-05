function [x,fval,it] = projection_bfgs(f,gradf,hessf,a,b,x0,itmax,tol)
	
	x = x0;
	it = 0;
	stop = false;

	% Check the stop criteria for x0
	if( norm(feval(gradf,x)) < tol )
		stop = true;
	end
	
	A = feval(hessf,x);

	while( ~stop )
		it = it + 1;
	
		d = A \ -feval(gradf,x);
	
		sigma = powell_increment(f, gradf, x, d, 0.01, 0.9);
		
		u = x;
		x = projection( x+sigma*d, a, b);
		
		s = x-u;
		y = feval(gradf,x) - feval(gradf,u);
		A = A - (A*s * (A*s)')/(s'*A*s) + (y*y')/(y'*s);
	
		% Stop criteria
		if( norm(feval(gradf,x)) < tol )
			stop = true;
		end
	
		% Too many iteration
		if( it >= itmax)
			stop = true;
		end
	end
	fval = feval(f,x);
end

function w = projection(v,a,b)
	% return max(a,min(v,b));
	n = length(v);
	if (length(a) ~= n || length(b) ~= n)
		error ('We have dimension problem here.');
	end
	for k=1:n
		if (a(k) > b(k))
			error ('It should be a <= b.');
		end
    end
	w = max(a,min(v,b));
end

function sigma = powell_increment(f, gradf, x, d, delta, beta)
	sigma = 1;
	a = sigma;
	b = sigma;
	if( powell_g1(sigma,f,gradf,x,d) >= delta )
		if( powell_g2(sigma,gradf,x,d) <= beta )
			return
		else % sigma liegt in I1
			while( powell_g1(b,f,gradf,x,d) >= delta )
				b = 2*b;
			end
		end
	else
		while( powell_g1(a,f,gradf,x,d) < delta || powell_g2(a,gradf,x,d) <= beta )
			a = a/2;
		end
	end
	while( true )
	sigma = (a+b)/2;
		if( powell_g1(sigma,f,gradf,x,d) >= delta )
			if( powell_g2(sigma,gradf,x,d) <= beta )
				return
			else % sigma liegt in I1
				a = sigma;
			end
		else
			b = sigma;
		end
	end
end

function value = powell_g1(sigma, f, gradf, x, d)
	if( sigma == 0 )
		value = 1;
	else
		value = ( feval(f,x+sigma*d) - feval(f,x) ) / (sigma*feval(gradf,x)'*d);
	end
end

function value = powell_g2(sigma, gradf, x, d)
	value = (feval(gradf,x+sigma*d)'*d)/(feval(gradf,x)'*d);
end