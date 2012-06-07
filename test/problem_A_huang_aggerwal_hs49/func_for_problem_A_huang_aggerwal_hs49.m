%problem_A_huang_aggerwal_hs49
%(Vgl. Problem 49 in \cite[S.~72]{hock})
%\min_{x\in\R^5}\ (x_1-x_2)^2 + (x_3-1)^2 + (x_4-1)^2 + (x_5-1)^6 
%\nb x_1 + x_2 + x_3 + 4 x_4 & = 7 \\
%x_3 + 5 x_5 & = 6 \\
%
function y = func_for_problem_A_huang_aggerwal_hs49(x)
	y = (x(1)-x(2))^2 + (x(3)-1)^2 + (x(4)-1)^2 + (x(5)-1)^6;
end