function test_quad_func()
	lambda = 1;
	a = [-10];
	b = [10;
	x0 = [40];
	tol = 0.001;
	itmax = 100;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('quad_func','grad_quad_func','hess_quad_func',lambda,a,b,x0,itmax,tol)
	t_ssn = toc
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('quad_func_v0','grad_quad_func_v0','hess_quad_func_v0',A,c,x0,itmax,tol)
	t_sqp = toc
end