function H = hess_func_for_problem_v_norm_1(x)
	xd = [4; 2; 7];
	H = 2*eye(length(x));
end