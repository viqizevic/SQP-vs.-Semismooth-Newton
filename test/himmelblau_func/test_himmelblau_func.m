function test_himmelblau_func()
	lambda = 0.000000001;
	a = [-20; -20];
	b = [20; 20];
	x0 = [5; 5];
	tol = 0.0001;
	itmax = 2000;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('himmelblau_func','grad_himmelblau_func','hess_himmelblau_func',lambda,a,b,x0,itmax,tol)
	t_ssn = toc
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('himmelblau_func_v0','grad_himmelblau_func_v0','hess_himmelblau_func_v0',A,c,x0,itmax,tol)
	t_sqp = toc
end