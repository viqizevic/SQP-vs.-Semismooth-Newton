function g = grad_exp_func_1(x)
	g = approx_gradient('exp_func_1',x,0.001);
end