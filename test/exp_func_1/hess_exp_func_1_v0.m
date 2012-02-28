function H = hess_exp_func_1_v0(x)
	lambda = 1;
	H = hess_exp_func_1(x) + lambda*eye(length(x));
end