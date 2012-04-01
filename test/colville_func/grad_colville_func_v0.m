function g = grad_colville_func_v0(x)
	lambda = 0.001;
	g = grad_colville_func(x) + lambda*x;
end