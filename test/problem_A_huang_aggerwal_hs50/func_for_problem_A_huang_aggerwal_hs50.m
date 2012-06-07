%problem_A_huang_aggerwal_hs50
%(Vgl. Problem 50 in \cite[S.~73]{hock})
%\min_{x\in\R^5}\ (x_1-x_2)^2 + (x_2-x_3)^2 + (x_3-x_4)^4 + (x_4-x_5)^2 
%\nb x_1 + 2 x_2 + 3 x_3 & = 6 \\
%x_2 + 2 x_3 + 3 x_4 & = 6 \\
%x_3 + 2 x_4 + 3 x_5 & = 6 \\
%
function y = func_for_problem_A_huang_aggerwal_hs50(x)
	y = (x(1)-x(2))^2 + (x(2)-x(3))^2 + (x(3)-x(4))^4 + (x(4)-x(5))^2;
end