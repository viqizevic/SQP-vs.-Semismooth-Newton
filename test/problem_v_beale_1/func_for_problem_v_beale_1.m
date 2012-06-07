%problem_v_beale_1
%\min_{x\in\R^2}\ (1.5-x_1 (1-x_2))^2+(2.25-x_1 (1-x_2^2))^2+(2.625-x_1 (1-x_2^3))^2 
%\nb 3 \leq x_1 & \leq 10 \\
%-10 \leq x_2 & \leq 10 \\
%
function y = func_for_problem_v_beale_1(x)
	y = (1.5-x(1)*(1-x(2)))^2+(2.25-x(1)*(1-x(2)^2))^2+(2.625-x(1)*(1-x(2)^3))^2;
end