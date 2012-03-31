function g = grad_dixon_func_v0(x)
	lambda = 0.0001;
	g = grad_dixon_func(x) + lambda*x;
end