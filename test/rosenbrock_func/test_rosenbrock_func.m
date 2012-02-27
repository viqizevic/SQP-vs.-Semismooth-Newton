function test_rosenbrock_func()
	lambda = 0.000000001;
	a = [-10; -20];
	b = [10; 20];
	x0 = [-1.9; 2];
	tol = 0.0001;
	itmax = 2000;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('rosenbrock_func','grad_rosenbrock_func','hess_rosenbrock_func',lambda,a,b,x0,itmax,tol)
	t_ssn = toc
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('rosenbrock_func_v0','grad_rosenbrock_func_v0','hess_rosenbrock_func_v0',A,c,x0,itmax,tol)
	t_sqp = toc
end