function g = grad_quad_func_1(x)
	xd = [4; 7];
	g = approx_gradient('quad_func_1',x,0.001);
end