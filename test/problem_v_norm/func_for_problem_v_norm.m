%problem_v_norm
%\min_{x\in\R^2}\ (x_1 - 4)^2 + (x_2 - 7)^2
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_norm(x)
	xd = [4; 7];
	y = norm(x-xd)^2;
end