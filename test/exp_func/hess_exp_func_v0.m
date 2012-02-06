function H = hess_exp_func_v0(x)
	lambda = 1;
	H = hess_exp_func(x) + lambda*eye(length(x));
endfunction