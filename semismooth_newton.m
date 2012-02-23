% Function: [x,fval,it] = semismooth_newton(f,gradf,hessf,lamda,a,b,x0,itmax,tol)
%
%  Semismooth-Newton-Method solves the problem
%        min ( f(x) + (lambda/2)*|x|^2 )
%         x
%        s.t. a <= x <= b
%
%  Let f : R^n -> R
%  lambda a real number
%  a and b in R^n
function [x,fval,it] = semismooth_newton(f,gradf,hessf,lambda,a,b,x0,itmax,tol)
	x = x0;
	n = length(x);
	it = 0;
	stop = false;
	
	while( ~stop )
		it = it + 1;
		A = (1/lambda) * grad_projection((-1/lambda)*feval(gradf,x),a,b) * feval(hessf,x) + eye(n);
		c = projection((-1/lambda)*feval(gradf,x),a,b) - x;
		d = A\c;
    sigma = armijo_increment(f,gradf,lambda,x,d,0.75,4,0.5);
		x = x + sigma*d;
		% Check the stop criteria
		if (norm(x-projection((-1/lambda)*feval(gradf,x),a,b)) < tol)
			stop = true;
		end
		% If there are too many iterations
		if (it >= itmax)
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

function W = grad_projection(v,a,b)
	% W = [w_ij]
	% w_kk := 1 if the projection of v_k equals to v_k
	% otherwise w_kk := 0
	% for k in {1,...,n}
	% w_ij = 0 if i ~= j
	w = projection(v,a,b);
	n = length(v);
	for k=1:n
		if (w(k) == v(k))
			w(k) = 1;
		else
			w(k) = 0;
		end
    end
	W = diag(w);
end

function sigma = armijo_increment(f,gradf,lambda,x,d,delta,gamma,beta)
    sigma = -(gamma/(norm(d)^2))*complete_gradf(gradf,lambda,x)'*d;
    while( true )
        if( complete_f(f,lambda,x+sigma*d) <= complete_f(f,lambda,x)+delta*sigma*complete_gradf(gradf,lambda,x)'*d )
            break; % fertig
        end
        % verkleinere sigma
        sigma = beta * sigma;
    end
end

function y = complete_f(f,lambda,x)
    y = feval(f,x) + (lambda/2)*norm(x)^2;
end

function g = complete_gradf(gradf,lambda,x)
    g = feval(gradf,x) + lambda*x;
end