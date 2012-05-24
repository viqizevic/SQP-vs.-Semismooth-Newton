function g = grad_func_for_problem_v_lin_regression(x)
	eta = [-0.5; -2; -3; -3; -2.5; -2; -1; 1; 3; 5.5];
	xi = [1:1:10]';
	g = approx_gradient('func_for_problem_v_lin_regression',x,0.00001);
end