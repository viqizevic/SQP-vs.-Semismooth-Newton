function test_exp_func()
	lambda = 1;
	a = -5;
	b = 5;
	x0 = 4;
	itmax = 10;
	tol = 0.001;
	printf "semismooth_newton\n";
	[x,it] = semismooth_newton('exp_func','grad_exp_func','hess_exp_func',lambda,a,b,x0,itmax,tol)
	A = [ -1;
				1 ];
	c = [ -a;
				b ];
	printf "sqp\n";
	[x,it] = sqp('exp_func_v0','grad_exp_func_v0','hess_exp_func_v0',A,c,x0,itmax,tol)
endfunction