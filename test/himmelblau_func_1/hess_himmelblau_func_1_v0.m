function H = hess_himmelblau_func_1_v0(x)
	lambda = 0.01;
	H = hess_himmelblau_func_1(x) + lambda*eye(length(x));
end