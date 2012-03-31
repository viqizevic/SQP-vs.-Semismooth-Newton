function g = grad_dixon_func_3_v0(x)
	lambda = 0.001;
	g = grad_dixon_func_3(x) + lambda*x;
end