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
function [x,fval,it] = active_set_strategy(f,gradf,hessf,lambda,a,b,x0,m0,itmax,tol)
	x = x0;
	m = m0;
	n = length(x);
	it = 0;
	stop = false;
	
	while( ~stop )
		it = it + 1;
		
		v = zeros(n,1);
		for k=1:n
			%if (m(k) + lambda*(x(k)-b(k)) > 0)
			if ( x(k) > b(k) )
				v(k) = 1;
			else
				%if (m(k) + lambda*(x(k)-a(k)) < 0)
				if ( x(k) < a(k) )
					v(k) = 3;
				else
					v(k) = 2;
				end
			end
		end
		
		A = [lambda*eye(n) eye(n);
              zeros(n,2*n)];
		y = zeros(n,1);
		for k=1:n
			if (v(k) == 2)
				A(n+k,n+k) = 1;
			else
				A(n+k,k) = 1;
				if (v(k) == 3)
					y(k) = a(k);
				else
					y(k) = b(k);
				end
			end
		end
		fh = @(u) A*[u(1:n,1);u(n+1:2*n,1)] + [feval(gradf,u(1:n,1));-y];
		fhjac = @(u) A + [feval(hessf,u(1:n,1)) zeros(n,n); zeros(n,2*n)];
		[w,it2] = newton_solve(fh,fhjac,[x;m],itmax,tol);
		it = it + it2;
		x = w(1:n,1);
		m = w(n+1:2*n,1);
		
		% Check the stop criteria
		d = feval(gradf,x)+lambda*x+m;
		if (norm(d) < tol)
			complete = true;
			w = max(zeros(n,1),m+lambda*(x-b))+min(zeros(n,1),m+lambda*(x-a));
			for k=1:n
				if ( abs(m(k)-w(k)) > tol )
				%if m(k) < 0
					complete = false;
				end
			end
			if complete
				stop = true;
			end
		end
		% If there are too many iterations
		if (it >= itmax)
			stop = true;
		end
	end
	fval = complete_f(f,lambda,x);
end

function [w,it] = newton_solve(fvec,fjac,w0,itmax,tol)
	w = w0;
	k = 0;
	stop = false;
	if (norm(fvec(w)) < tol)
		stop = true;
	end
	while (~stop)
		k = k+1;
		d = - fjac(w) \ fvec(w);
		w = w + d;
		if (norm(fvec(w)) < tol)
			stop = true;
		end
		if (k >= itmax)
			stop = true;
		end
	end
	it = k;
end

function y = complete_f(f,lambda,x)
	y = feval(f,x) + (lambda/2)*norm(x)^2;
end