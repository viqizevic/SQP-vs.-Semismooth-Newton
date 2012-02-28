function H = hess_quad_func_2_v0(x)
	lambda = 1;
	H = hess_quad_func_2(x) + lambda*eye(length(x));
end