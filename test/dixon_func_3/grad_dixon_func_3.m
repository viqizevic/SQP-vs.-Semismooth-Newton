function g = grad_dixon_func_3(x)
	n = 20;
	g(1,1) = -2*(1-x(1)) + 4*x(1)*(x(1)^2-x(2));
    for k=2:(n-1)
		g(k,1) = -2*(x(k-1)^2-x(k)) + 4*x(k)*(x(k)^2-x(k+1));
	end
	g(n,1) = -2*(x(n-1)^2-x(n)) - 2*(1-x(n));
end