function test_exp_func()
	lambda = 1;
	a = [-10; -2];
	b = [10; 20];
	x0 = [4; 7];
	tol = 0.001;
	itmax = 500;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('exp_func','grad_exp_func','hess_exp_func',lambda,a,b,x0,itmax,tol)
	t_ssn = toc
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('exp_func_v0','grad_exp_func_v0','hess_exp_func_v0',A,c,x0,itmax,tol)
	t_sqp = toc
end