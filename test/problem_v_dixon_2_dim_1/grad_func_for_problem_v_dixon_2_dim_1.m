function g = grad_func_for_problem_v_dixon_2_dim_1(x)
	g = [ -2*(1-x(1)) + 4*x(1)*(x(1)^2-x(2));
	         -2*(x(1)^2-x(2)) - 2*(1-x(2)) ];
end