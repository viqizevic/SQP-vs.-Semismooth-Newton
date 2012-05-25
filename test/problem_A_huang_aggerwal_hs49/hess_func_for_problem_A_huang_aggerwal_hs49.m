function H = hess_func_for_problem_A_huang_aggerwal_hs49(x)
	H = approx_hessian('func_for_problem_A_huang_aggerwal_hs49',x,0.00001);
end