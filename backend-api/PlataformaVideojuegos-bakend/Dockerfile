FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

COPY ["WebApplication1.csproj", "./"]
RUN dotnet restore "./WebApplication1.csproj"

COPY . .
RUN dotnet publish "WebApplication1.csproj" -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS final
WORKDIR /app

COPY --from=build /app/publish .
COPY VideojuegosGameStore.db /app/VideojuegosGameStore.db

EXPOSE 8080

ENTRYPOINT ["dotnet", "WebApplication1.dll"]