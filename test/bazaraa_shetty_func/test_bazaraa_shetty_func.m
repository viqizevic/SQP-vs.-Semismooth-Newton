function test_bazaraa_shetty_func()
	lambda = 0.000000001;
	a = [-20; -20];
	b = [20; 20];
	x0 = [5; 5];
	tol = 0.0001;
	itmax = 2000;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('bazaraa_shetty_func','grad_bazaraa_shetty_func','hess_bazaraa_shetty_func',lambda,a,b,x0,itmax,tol)
	t_ssn = toc
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('bazaraa_shetty_func_v0','grad_bazaraa_shetty_func_v0','hess_bazaraa_shetty_func_v0',A,c,x0,itmax,tol)
	t_sqp = toc
end