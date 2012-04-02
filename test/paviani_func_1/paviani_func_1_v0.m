function y = paviani_func_1_v0(x)
	lambda = 0.001;
	y = paviani_func_1(x) + (lambda/2)*norm(x)^2;
end