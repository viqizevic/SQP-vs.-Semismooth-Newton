function test_quad_func_1()
	lambda = 1;
	a = [-10; -2];
	b = [10; 20];
	x0 = [40; 70];
	tol = 0.001;
	itmax = 100;
	[x,it] = semismooth_newton('quad_func_1','grad_quad_func_1','hess_quad_func_1',lambda,a,b,x0,itmax,tol)
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	[x,it] = sqp('quad_func_1_v0','grad_quad_func_1_v0','hess_quad_func_1_v0',A,c,x0,itmax,tol)
end