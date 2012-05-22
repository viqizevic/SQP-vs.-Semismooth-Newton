function g = grad_func_for_problem_v_exp(x)
	g = 2*exp(norm(x)^2)*x;
end