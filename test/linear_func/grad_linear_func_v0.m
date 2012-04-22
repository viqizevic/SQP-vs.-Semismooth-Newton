function g = grad_linear_func_v0(x)
	lambda = 0.0001;
	g = grad_linear_func(x) + lambda*x;
end