function g = grad_exp_func_v0(x)
	lambda = 1;
	g = grad_exp_func(x) + lambda*x;
end