%problem_v_norm_1
%\min_{x\in\R^3}\ (x_1 - 4)^2 + (x_2 - 2)^2 + (x_3 - 7)^2
%\nb 5 \leq x_i & \leq 10,\ i = 1,2,3 \\
%
function y = func_for_problem_v_norm_1(x)
	xd = [4; 2; 7];
	y = norm(x-xd)^2;
end