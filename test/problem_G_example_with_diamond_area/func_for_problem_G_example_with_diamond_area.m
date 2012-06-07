%problem_G_example_with_diamond_area
%\min_{x\in\R^2}\ x_1^2 + x_2^2 - 4 x_1 - 4 x_2
%\nb 2 x_1 + x_2 & \leq 2 \\
%x_1 - x_2 & \leq 1 \\
%-x_1 - x_2 & \leq 1 \\
%-2 x_1 + x_2 & \leq 2 \\
%
function y = func_for_problem_G_example_with_diamond_area(x)
	Q = [2 0; 0 2];
	q = [-4; -4];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end