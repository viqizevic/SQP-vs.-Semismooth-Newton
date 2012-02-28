function test_quad_func_1()
	lambda = 1;
	a = [-3; -6];
	b = [10; 20];
	x0 = [8; 17];
	tol = 0.001;
	itmax = 100;
	tic;
	[x_ssn,fval_ssn,it_ssn] = semismooth_newton('quad_func_1','grad_quad_func_1','hess_quad_func_1',lambda,a,b,x0,itmax,tol);
	t_ssn = toc;
	x1 = sprintf('%.3f ',x_ssn);
	f1 = sprintf('f(x_ssn) = %.3f ',fval_ssn);
	t1 = sprintf('solved in %.2f ms.',t_ssn*1000);
	str1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];
	disp(str1);
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	tic;
	[x_sqp,fval_sqp,it_sqp] = sqp('quad_func_1_v0','grad_quad_func_1_v0','hess_quad_func_1_v0',A,c,x0,itmax,tol);
	t_sqp = toc;
	x2 = sprintf('%.3f ',x_sqp);
	f2 = sprintf('f(x_sqp) = %.3f ',fval_sqp);
	t2 = sprintf('solved in %.2f ms.',t_sqp*1000);
	str2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];
	disp(str2);
end