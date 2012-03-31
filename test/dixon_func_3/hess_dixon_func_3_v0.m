function H = hess_dixon_func_3_v0(x)
	lambda = 0.001;
	H = hess_dixon_func_3(x) + lambda*eye(length(x));
end