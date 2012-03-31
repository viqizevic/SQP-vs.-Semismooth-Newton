function g = grad_quad_func_2(x)
	xd = [4; 7];
	g = approx_gradient('quad_func_2',x,0.001);
end