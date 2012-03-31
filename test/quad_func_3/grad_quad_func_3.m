function g = grad_quad_func_3(x)
	xd = [4; 7; 10];
	g = approx_gradient('quad_func_3',x,0.001);
end