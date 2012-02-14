function H = hess_quad_func_1_v0(x)
	lambda = 1;
	H = hess_quad_func_1(x) + lambda*eye(length(x));
end