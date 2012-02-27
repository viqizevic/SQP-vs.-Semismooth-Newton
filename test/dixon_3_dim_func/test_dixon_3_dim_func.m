function test_dixon_3_dim_func()
	lambda = 0.0000000001;
	a = [-10; -10; -10];
	b = [10; 10; 10];
	x0 = [1.5; 1.5; 1.5];
	tol = 0.001;
	itmax = 500;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('dixon_3_dim_func','grad_dixon_3_dim_func','hess_dixon_3_dim_func',lambda,a,b,x0,itmax,tol)
	t_ssn = toc
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('dixon_3_dim_func_v0','grad_dixon_3_dim_func_v0','hess_dixon_3_dim_func_v0',A,c,x0,itmax,tol)
	t_sqp = toc
end