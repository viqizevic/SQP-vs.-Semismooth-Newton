function test_quad_func_1()
	lambda = 1;
	a = [-10; -2];
	b = [10; 20];
	x0 = [40; 70];
	tol = 0.001;
	itmax = 100;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('quad_func_1','grad_quad_func_1','hess_quad_func_1',lambda,a,b,x0,itmax,tol)
	t_ssn = toc
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('quad_func_1_v0','grad_quad_func_1_v0','hess_quad_func_1_v0',A,c,x0,itmax,tol)
	t_sqp = toc
end