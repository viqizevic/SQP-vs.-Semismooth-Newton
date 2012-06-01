function H = hess_func_for_problem_v_beale_1(x)
	H = approx_hessian('func_for_problem_v_beale_1',x,0.00001);
end