function test_bazaraa_shetty_func()
	lambda = 0.000000001;
	a = [-20; -20];
	b = [20; 20];
	x0 = [5; 5];
	tol = 0.0001;
	itmax = 2000;
	[x,it] = semismooth_newton('bazaraa_shetty_func','grad_bazaraa_shetty_func','hess_bazaraa_shetty_func',lambda,a,b,x0,itmax,tol)
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	[x,it] = sqp('bazaraa_shetty_func_v0','grad_bazaraa_shetty_func_v0','hess_bazaraa_shetty_func_v0',A,c,x0,itmax,tol)
end