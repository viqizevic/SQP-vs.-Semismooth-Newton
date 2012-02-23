function test_rosenbrock_func()
	lambda = 0.000000001;
	a = [-10; -20];
	b = [10; 20];
	x0 = [-1.9; 2];
	tol = 0.0001;
	itmax = 2000;
	[x,fval,it] = semismooth_newton('rosenbrock_func','grad_rosenbrock_func','hess_rosenbrock_func',lambda,a,b,x0,itmax,tol)
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	[x,fval,it] = sqp('rosenbrock_func_v0','grad_rosenbrock_func_v0','hess_rosenbrock_func_v0',A,c,x0,itmax,tol)
end