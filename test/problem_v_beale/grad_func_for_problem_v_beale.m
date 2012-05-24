function g = grad_func_for_problem_v_beale(x)
	g = approx_gradient('func_for_problem_v_beale',x,0.00001);
end